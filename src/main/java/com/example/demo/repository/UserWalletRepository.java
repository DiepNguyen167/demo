package com.example.demo.repository;
import java.util.Optional;

import com.example.demo.model.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {

    Optional<UserWallet> findUserWalletByUserInfoId(long userInfoId);
}
