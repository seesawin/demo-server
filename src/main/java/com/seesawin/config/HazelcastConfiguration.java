package com.seesawin.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@Configuration
@EnableHazelcastHttpSession
public class HazelcastConfiguration {
    /**
     * 建立一個預設名為hazelcast-instance的HazelcastInstance例項；
     * 使用預設的組播發現模式，組播傳播地址預設為：224.2.2.3，如果想修改資訊或修改為TCP模式可通過setNetworkConfig()介面設定相關資訊；
     * 建立一個名為dev，訪問密碼為dev-pass的group保障節點加入，如果想修改組，可通過setGroupConfig()介面設定相關資訊；
     * 建立了一個名為instruments的分散式map資料結構，並設定了該map的最大容量200／逐出策略LRU／有效期20000ms等資訊，當叢集啟動後，我們可以在任一成員節點上通過HazelcastInstance讀寫該map。
     */
    @Bean
    public Config hazelCastConfigWithMancenter() {
        //如果有叢集管理中心，可以配置
        ManagementCenterConfig centerConfig = new ManagementCenterConfig();
        centerConfig.setUrl(" http://localhost:8080/hazelcast-mancenter");
        centerConfig.setEnabled(true);
        return new Config()
                .setInstanceName("hazelcast-instance")
                .setManagementCenterConfig(centerConfig)
                .setProperty("hazelcast.rest.enabled", "true")
                .addMapConfig(
                        new MapConfig()
                                .setName("TestService")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(10));
    }
}
