package com.projet.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projet.Model.Section;
import com.projet.Model.Student;
import com.projet.Repository.SectionRepository;
import com.projet.Repository.StudentRepository;

@RestController
@RequestMapping("/api/v1")
public class SectionController {
	@Autowired
    private  SectionRepository sectionRepository;

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/sections")
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @GetMapping("/sections/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable(value = "id" )
                                                          Integer id) throws ResourceNotFoundException {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No section with this id!" + id));
        return ResponseEntity.ok().body(section);
    }

    @PostMapping("/sections")
    public Section createSection(@Valid @RequestBody Section section) {
        return sectionRepository.save(section);
    }

    @PutMapping("/sections/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable(value = "id") Integer id ,
                                                 @Valid @RequestBody Section sectionDetails) throws ResourceNotFoundException {
        Section section = sectionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No section for this id" + id));

        section.setName(sectionDetails.getName());
        final Section updatedSection = sectionRepository.save(section);
        return ResponseEntity.ok(updatedSection);
    }

    @DeleteMapping("/sections/{id}")
    public Map<String, Boolean> deleteSection(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Section section = sectionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No section for this id" + id));
        List <Student> allStudents = studentRepository.findAll();
        for (Student student: allStudents) {
            if (student.getSection().getId().equals(id)) {
                Student studentToDelete = studentRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("No student for this id" + id));
                studentRepository.delete(studentToDelete);
            }
        }
        sectionRepository.delete(section);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
