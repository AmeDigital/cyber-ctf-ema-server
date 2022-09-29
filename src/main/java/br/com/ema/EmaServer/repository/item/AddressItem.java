package br.com.ema.EmaServer.repository.item;

import br.com.ema.EmaServer.model.Address;
import br.com.ema.EmaServer.model.Order;

import javax.persistence.*;

@Entity
@Table(name = "TB_ADDRESS")
public class AddressItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "latitude", length = 100, nullable = false)
    private Double latitude;

    @Column(name = "longitude", length = 100, nullable = false)
    private Double longitude;

    @Column(name = "state", length = 100, nullable = false)
    private String state;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "district", length = 100, nullable = false)
    private String district;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Column(name = "cep", length = 255, nullable = false)
    private String cep;

    public AddressItem(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public String getCep() {
        return cep;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Address toModel() {
        Address address = new Address();
        address.setId(this.getId());
        address.setLatitude(this.getLatitude());
        address.setLongitude(this.getLongitude());
        address.setUf(this.getState());
        address.setBairro(this.getDistrict());
        address.setCidade(this.getCity());
        address.setEndereco(this.getAddress());
        address.setCategoria(this.getCategory());
        address.setCep(this.getCep());

        return address;
    }

    public Double doCalculateEuclidianDistance(Double lat,Double longi){
        double alpha = Math.acos(Math.cos(this.latitude) * Math.cos(lat) * Math.cos(this.longitude - longi) + Math.sin(this.latitude) * Math.sin(lat));
        double euclidianDistance = 6371000*alpha;
        return euclidianDistance;
    }
}
