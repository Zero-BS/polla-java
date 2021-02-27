package org.zerobs.polla.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.zerobs.polla.entities.db.User;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
}