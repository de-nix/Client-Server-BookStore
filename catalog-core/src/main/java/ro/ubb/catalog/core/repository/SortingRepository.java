package ro.ubb.catalog.core.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import ro.ubb.catalog.core.model.BaseEntity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@NoRepositoryBean
public interface SortingRepository< T extends BaseEntity<ID>,ID extends Serializable> extends Repository< T,ID>
{

//
//   default List<T> findAll(Sort sort){
//       List<T> list = this.findAll();
//       list.sort((Comparator<? super T>) sort);
//       return list;
//   };

    //TODO: insert sorting-related code here
}