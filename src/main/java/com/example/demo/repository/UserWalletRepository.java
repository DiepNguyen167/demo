package com.example.demo.repository;

import com.example.demo.model.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {

    Optional<UserWallet> findUserWalletByUserInfoId(long userInfoId);

    @Query(value = """
            SELECT * FROM USER_WALLET uw
            WHERE uw.user_info_id = :userInfoId
            AND uw.currency = :baseCurrency)
""", nativeQuery = true)
    UserWallet findByUserInfoIdAndCurrency(long userInfoId, String baseCurrency);
}
