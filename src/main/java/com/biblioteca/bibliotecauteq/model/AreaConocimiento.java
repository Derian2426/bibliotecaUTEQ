package com.biblioteca.bibliotecauteq.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AreaConocimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaConocimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArea")
    private Long idArea;
    @Column(name = "nombreArea", nullable = false, length = 120)
    private String nombreArea;

    public AreaConocimiento areaConocimiento(LibroRequest libroRequest){
        try {
            AreaConocimiento areaConocimiento= new AreaConocimiento();
            areaConocimiento.setIdArea(libroRequest.getLibro().getSubAreasEspecificas().getSubAreasConocimiento().getAreaConocimiento().getIdArea());
            areaConocimiento.setNombreArea(libroRequest.getLibro().getSubAreasEspecificas().getSubAreasConocimiento().getAreaConocimiento().getNombreArea());
            return areaConocimiento;
        }catch (Exception e){
            return new AreaConocimiento();
        }
    }
}
