package au.com.qantas.loyalty.lsl.candidatetask.member.repository;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProgramRepository extends CrudRepository<ProgramEntity, String> {

  List<ProgramEntity> findAll();
}
