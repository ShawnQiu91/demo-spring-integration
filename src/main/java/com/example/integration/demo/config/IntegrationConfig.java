package com.example.integration.demo.config;

import com.example.integration.demo.service.ConvertEquService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.messaging.Message;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableIntegration
public class IntegrationConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getMyDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 将HES系统的数据从数据库中抓取，处理完成后发送到指定的消息队列，云平台从消息队列中消费数据
     */
    /**
     * 从数据源抓取设备信息
      * @return
     */
    @Bean
    public MessageSource<Object> jdbcMessageSource() {
        return new JdbcPollingChannelAdapter(getMyDataSource(), "SELECT * FROM equipment");
    }

    /**
     * java DSL配置
     * 从数据源获取数据并放入channel "equ-channel-input"
     * 使用任务，每1000ms执行一次
     *
     * @return
     */
    @Bean
    public IntegrationFlow pollingFlow() {
        return IntegrationFlows.from(jdbcMessageSource(), c -> c.poller(Pollers.fixedRate(10000).maxMessagesPerPoll(1)))
                .channel("equ-channel-input")
                .get();
    }

    /**
     * java DSL配置
     * 处理equ-channel-output中的数据,将数据放入消息队列
     *
     * @return
     */
    @Bean
    public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
        return IntegrationFlows
                .from("equ-channel-output")
                .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(RabbitMQConfig.EQU_EXCHANGE))
                .get();
    }

    /**
     * 将从equ-channel-input获取的数据处理后放入equ-channel-output
     * 数据处理
     *
     * @param msg
     * @return
     */
    @ServiceActivator(inputChannel = "equ-channel-input", outputChannel = "equ-channel-output")
    public Message<List<Object>> getDbData(Message<List<Object>> msg) {
        return new ConvertEquService().getSource(msg).convert();
    }


}


