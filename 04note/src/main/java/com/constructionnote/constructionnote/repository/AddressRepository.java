package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    Address findByAddressCode(String addressCode);
    List<Address> findByAddressCodeIn(List<String> addressCodes);

    @Query(
            value = "SELECT address_code " +
                    "FROM Address " +
                    "WHERE lat BETWEEN :minLat AND :maxLat " +
                    "AND lng BETWEEN :minLon AND :maxLon " +
                    "AND ST_Distance_Sphere(POINT(:lng, :lat), POINT(lng, lat)) <= 1000 ",
            nativeQuery = true
    )
    List<String> getNearbyAddressCodeByBoundingBox(@Param("lat") double lat,
                                                   @Param("lng") double lng,
                                                   @Param("minLat") double minLat,
                                                   @Param("minLon") double minLon,
                                                   @Param("maxLat")double maxLat,
                                                   @Param("maxLon") double maxLon);
}
