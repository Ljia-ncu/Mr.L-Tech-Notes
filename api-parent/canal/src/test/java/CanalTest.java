import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mrl.canal.CanalApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetSocketAddress;

/**
 * @ClassName: CanalTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/29 17:32
 * @Version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CanalApplication.class)
public class CanalTest {

    /**
     * 一个Message对应一个批次 =》batchId
     * 一个批次包含多个Entry
     * 一个Entry对应一个RowChange事件 =》rowChange.getRowDatasList()
     * rowChange.getRowDatasList() 包含该行数据每一列的状态信息
     * 再根据RowChange类型 进行处理
     *
     * @throws InterruptedException
     */
    @Test
    public void cluster() throws InterruptedException {
        //destination:example 代表canal server的一个实例
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(), 11111), "example", "", "");
        connector.connect();
        connector.subscribe(".*\\..*");
        //恢复到之前同步的位置
        connector.rollback();

        int emptyCount = 0;
        int total = 1200;
        while (emptyCount < total) {
            //遍历结束后再进行确认
            Message message = connector.getWithoutAck(1000);
            long batchId = message.getId();
            int size = message.getEntries().size();
            if (batchId == -1 || size == 0) {
                emptyCount++;
                Thread.sleep(1000);
            } else {
                emptyCount = 0;
                message.getEntries().forEach(entry -> {
                    try {
                        CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                        CanalEntry.Header header = entry.getHeader();
                        log.info("binlog-{}-{},name-{}-{},eventType{}", header.getLogfileName(), header.getLogfileOffset(), header.getSchemaName(), header.getTableName(), rowChange.getEventType());

                        rowChange.getRowDatasList().forEach(rowData -> {
                            switch (rowChange.getEventType()) {
                                case INSERT:
                                    rowData.getAfterColumnsList().forEach(column -> {
                                        System.out.println(column.getName() + "/" + column.getValue() + "/" + column.getUpdated());
                                    });
                                    break;
                                case UPDATE:
                                    rowData.getBeforeColumnsList().forEach(column -> {
                                        System.out.println(column.getName() + "/" + column.getValue() + "/" + column.getUpdated());
                                    });
                                    rowData.getAfterColumnsList().forEach(column -> {
                                        System.out.println(column.getName() + "/" + column.getValue() + "/" + column.getUpdated());
                                    });
                                    break;
                                default:
                                    break;
                            }
                        });
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                });
            }
            connector.ack(batchId);
        }
        connector.disconnect();
    }
}