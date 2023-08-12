package com.biblioteca.bibliotecauteq.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SubAreasConocimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubAreasConocimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSubArea")
    private Long idSubArea;
    @Column(name = "nombreSubArea", nullable = false, length = 100)
    private String nombreSubArea;
    @ManyToOne
    @JoinColumn(name = "idArea")
    private AreaConocimiento areaConocimiento;
    public SubAreasConocimiento subAreasConocimiento(LibroRequest libroRequest){
        try {
            SubAreasConocimiento areasConocimiento= new SubAreasConocimiento();
            areasConocimiento.setIdSubArea(libroRequest.getLibro().getSubAreasEspecificas().getSubAreasConocimiento().getIdSubArea());
            areasConocimiento.setNombreSubArea(libroRequest.getLibro().getSubAreasEspecificas().getSubAreasConocimiento().getNombreSubArea());
            return areasConocimiento;
        }catch (Exception e){
            return new SubAreasConocimiento();
        }
    }
}
