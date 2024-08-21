package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Category;
import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.repository.ForumRepository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements ICategory {

    CategoryRepository categoryRepository;








}
