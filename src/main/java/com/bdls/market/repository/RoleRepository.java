package com.bdls.market.repository;


import com.bdls.market.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long>, PagingAndSortingRepository<Role, Long> {
    Optional<Role> findAllByName(String name);
}
