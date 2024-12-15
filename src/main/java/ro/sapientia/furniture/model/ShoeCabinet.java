package ro.sapientia.furniture.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "shoe_cabinet")
public class ShoeCabinet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_shoe_cabinet")
    @SequenceGenerator(name = "pk_shoe_cabinet", sequenceName = "pk_shoe_cabinet")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "height")
    private double height;

    @Column(name = "width")
    private double width;

    @Column(name = "depth")
    private double depth;

    @Column(name = "material")
    private String material;

    @Column(name = "shelves_count")
    private int shelvesCount;
}
