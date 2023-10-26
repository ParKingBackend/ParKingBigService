package com.example.parkingbigservice.service;

import com.example.parkingbigservice.model.Company;
import com.example.parkingbigservice.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }


    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public void updateCompany(Company company) {
        companyRepository.save(company);
    }
}
