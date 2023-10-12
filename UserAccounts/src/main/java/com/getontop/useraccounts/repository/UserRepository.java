package com.getontop.useraccounts.repository;


import com.getontop.useraccounts.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findByNationalId(String s);

    @Query(value="select * from users u where u.national_id= :nationalId", nativeQuery=true)
    Optional<User> findUserByNationalId(@Param("nationalId") String nationalId);

    @Query(value="select * from users u where u.account_number= :accountNumber", nativeQuery=true)
    Optional<User> findUserByaccountNumber(@Param("accountNumber") String accountNumber);


/*

    @Query(value="select * from users u where u.account_number= :accountNumber and u.national_id= :nationalId", nativeQuery=true)
    Optional<User> findUserByaccountNumberAndNationalId(@Param("nationalId") String nationalId, @Param("accountNumber") String accountNumber);

    @Query(value="select * from users u where u.account_number= :accountNumber and u.national_id= :nationalId and u.bank_name= :bankName", nativeQuery=true)
    Optional<User> findUserByaccountNumberAndNationalIdAndBankName(@Param("nationalId") String nationalId, @Param("bankName") String bankName, @Param("accountNumber") String accountNumber);

    @Query(value="select * from users u where u.account_number= :accountNumber and u.bank_name= :bankName", nativeQuery=true)
    Optional<User> findUserByaccountNumberAndBankName(@Param("bankName") String bankName, @Param("accountNumber") String accountNumber);


*/



}
