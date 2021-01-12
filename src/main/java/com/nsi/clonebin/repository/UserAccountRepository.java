package com.nsi.clonebin.repository;

import com.nsi.clonebin.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    UserAccount findByUsername(String username);
}
