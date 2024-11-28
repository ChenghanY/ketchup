import com.google.common.collect.Lists
import com.james.KetchupApplication
import com.james.db.mapper.FileOutputRecordMapper
import com.james.db.po.FileOutputRecordPO
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.annotation.Resource

@SpringBootTest(classes = KetchupApplication.class)
class GcSpec extends Specification {

    @Resource
    FileOutputRecordMapper mapper

    /**
     * 测试用例开始，生成十万条数据库数据
     */
    Long startId = 1;
    Long endId = 100000;

    def "单次查询" () {
        when:
        printCurrentMemory("查询前内存情况")

        def list = mapper.selectByIdRang(startId, endId)

        then:
        Throwable throwable = thrown()
        println(throwable.message)
    }

    def "分片查询" () {
        when:
        printCurrentMemory("查询前内存情况")

        // 分片查询
        def allIds = (startId..endId).toList()
        Lists.partition(allIds, 10000).forEach { subIds ->
            def first = subIds.first()
            def last = subIds.last()
            def subList = mapper.selectByIdRang(first, last)
        }
        printCurrentMemory("查询后的内存情况")

        then:
        noExceptionThrown()
    }

    private void printCurrentMemory(String remark) {
        def runtime = Runtime.getRuntime()
        println("=========== $remark =========== ")
        println("当前使用内存: ${(runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)} MB")
        println("待分配内存: ${runtime.freeMemory() / (1024 * 1024)} MB")
        println("=========== $remark =========== ")
    }

    def randomString() {
        def characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
        def sb = new StringBuilder()
        def maxLength = new Random().nextInt(15) + 1;

        (0..< maxLength).each {
            def index = new Random().nextInt(characters.length())
            sb.append(characters[index])
        }
        return sb.toString()
    }

    private FileOutputRecordPO createPO(long it) {
        def po = new FileOutputRecordPO()
        po.setId(it)
        po.setOutputType(randomString())
        po.setOutputState(randomString())
        po.setFileName(randomString())
        po.setFileRef(randomString())
        return po
    }

    Long batchSize = 1000;
    def setup() {
        def rang = (startId .. endId)
        List<FileOutputRecordPO> records = []
        rang.each {
            // 生成数据
            records.add(createPO(it))
            // 1000为分片插入数据库
            if (records.size() == batchSize) {
                mapper.batchInsertWithId(records);
                records.clear();
            }
        }

        if (records.size() > 0) {
            mapper.batchInsert(records)
            records.clear()
        }
    }

    /**
     * 测试用例结束，生成十万条数据库数据
     */
    def cleanup() {
        mapper.deleteByIdRang(startId, endId)
    }

}