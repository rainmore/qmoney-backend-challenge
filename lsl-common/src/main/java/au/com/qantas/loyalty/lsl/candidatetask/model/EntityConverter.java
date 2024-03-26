package au.com.qantas.loyalty.lsl.candidatetask.model;

public interface EntityConverter<E, T> {

  T convertFromEntity(E entity);

  E convertToEntity(T dto);

  void convertDtoToEntity(T dto, E entity);

}
