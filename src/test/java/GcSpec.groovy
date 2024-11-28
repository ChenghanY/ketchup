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
     * 十万条数据库查询
     */
    Long startId = 1;
    Long endId = 100000;

    def "全量查询" () {
        when:
        printCurrentMemory()
        //
        def list = mapper.selectByIdRang(startId, endId)

        then:
        Throwable throwable = thrown()
        println(throwable.message)
    }

    def "分片查询" () {
        when:
        printCurrentMemory()

        // 使用 Guava 的工具类进行数据分片
        def allIds = (startId..endId).toList()
        Lists.partition(allIds, 10000).forEach { subIds ->
            def subList = mapper.selectByIdRang(subIds.first(), subIds.last())
            println(subList.size())
        }
        printCurrentMemory()

        then:
        noExceptionThrown()
    }

    private void printCurrentMemory() {
        def runtime = Runtime.getRuntime()
        println("====================== ")
        println("当前使用内存: ${(runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)} MB")
        println("待分配内存: ${runtime.freeMemory() / (1024 * 1024)} MB")
        println("====================== ")
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


    /**
     * 生成十万条数据，测试用例执行完后删除
     */
    def setup() {
        def allIds = (startId..endId).toList()
        Lists.partition(allIds, 1000).forEach { subIds ->
            def sub = subIds.collect(it -> createPO(it))
            mapper.batchInsertWithId(sub);
        }
    }

    def cleanup() {
        mapper.deleteByIdRang(startId, endId)
    }
}
