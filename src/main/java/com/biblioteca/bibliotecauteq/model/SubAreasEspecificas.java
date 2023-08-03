package com.biblioteca.bibliotecauteq.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "SubAreasEspecificas")
@Data
public class SubAreasEspecificas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSubAreaEspecifica")
    private Long idSubAreaEspecifica;
    @Column(name = "nombreSubAreaEspecifica")
    private String nombreSubAreaEspecifica;
    @ManyToOne
    @JoinColumn(name = "idSubArea")
    private SubAreasConocimiento subAreasConocimiento;

   // @OneToMany(mappedBy = "subAreasEspecificas")
    //private List<Libro> libro;
    public SubAreasEspecificas subAreasEspecificas(LibroRequest libroRequest){
        try {
            SubAreasEspecificas areasEspecificas= new SubAreasEspecificas();
            areasEspecificas.setIdSubAreaEspecifica(libroRequest.getLibro().getSubAreasEspecificas().getIdSubAreaEspecifica());
            areasEspecificas.setNombreSubAreaEspecifica(libroRequest.getLibro().getSubAreasEspecificas().getNombreSubAreaEspecifica());
            return areasEspecificas;
        }catch (Exception e){
            return new SubAreasEspecificas();
        }
    }
}
