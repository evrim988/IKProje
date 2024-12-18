package org.example.ikproje.repository;

import org.example.ikproje.entity.Company;
import org.example.ikproje.view.VwUnapprovedAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}