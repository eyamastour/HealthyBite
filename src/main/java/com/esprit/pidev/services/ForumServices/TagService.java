package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Tag;
import com.esprit.pidev.repository.ForumRepository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TagService implements ITag{
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag retrieveTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag with id " + id + " not found"));
    }

}
