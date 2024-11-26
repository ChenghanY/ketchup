import com.james.KetchupApplication
import com.james.db.mapper.FileOutputRecordMapper
import com.james.db.po.FileOutputRecordPO
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.annotation.Resource

@SpringBootTest(classes = KetchupApplication.class)
class FileOutputRecordMapperSpec extends Specification{

    @Resource
    FileOutputRecordMapper mapper

    def "单条插入" () {
        given:
        def newPO = new FileOutputRecordPO()
        newPO.setFileName("test")
        newPO.setFileRef("testref")
        newPO.setOutputState("new")
        newPO.setOutputType("xxx")

        when:
        mapper.insertOne(newPO)

        then:
        newPO.getId() != null
    }

    def "单条查询" () {
        given:
        def one = mapper.queryOneById(2)

        expect:
        one.getFileName() != null
    }

    def "单条更新" () {
        given:
        def newPO = new FileOutputRecordPO();
        newPO.setFileRef("after")
        newPO.setId(2)

        when:
        mapper.updateOneById(newPO)

        then:
        mapper.queryOneById(2).getFileRef() == "after"
    }

    def "多条查询" () {
        given:
        def list = mapper.queryListByIds([1,2,3])

        expect:
        list != null
    }

    def "多条插入" () {
        given:
        def list = []
        for (i in 1..3) {
            def po = new FileOutputRecordPO()
            po.setFileName("test")
            po.setFileRef("testref")
            po.setOutputState("new")
            po.setOutputType("xxx")
            list.add(po)
        }

        when:
        mapper.batchInsert(list)

        then:
        mapper.queryOneById(2).getFileRef() == "after"
    }

    def "多条更新" () {
        given:
        def list = []
        for (i in 1..3) {
            def po = new FileOutputRecordPO()
            po.setFileName("after")
            po.setId(i)
            list.add(po)
        }

        when:
        mapper.batchUpdate(list)

        then:
        mapper.queryOneById(2).getFileRef() == "after"
    }
}
