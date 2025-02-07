package com.springboot.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.blogapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
