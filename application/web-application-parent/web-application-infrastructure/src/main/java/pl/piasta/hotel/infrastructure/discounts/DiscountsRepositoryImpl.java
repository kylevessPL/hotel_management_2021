package pl.piasta.hotel.infrastructure.discounts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.discounts.DiscountsRepository;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;
import pl.piasta.hotel.infrastructure.dao.DiscountsEntityDao;
import pl.piasta.hotel.infrastructure.mapper.DiscountsEntityMapper;
import pl.piasta.hotel.infrastructure.model.DiscountsEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DiscountsRepositoryImpl implements DiscountsRepository {

    private final DiscountsEntityMapper mapper;
    private final DiscountsEntityDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<DiscountDetails> getDiscounts() {
        return mapper.mapToDiscount(dao.findAll());
    }

    @Override
    @Transactional
    public Optional<DiscountDetails> getDiscountDetails(String code) {
        Optional<DiscountsEntity> discountsEntity = dao.findByCode(code);
        return mapper.mapToDiscountDetails(discountsEntity);
    }

    @Override
    @Transactional
    public void addDiscount(String code, Integer value) {
        DiscountsEntity discountsEntity = new DiscountsEntity();
        updateEntity(discountsEntity, code, value);
        dao.save(discountsEntity);
    }

    @Override
    @Transactional
    public int removeDiscount(String code) {
        return dao.deleteByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean discountExists(String code) {
        return dao.existsByCode(code);
    }

    void updateEntity(DiscountsEntity discountsEntity, String code, Integer value) {
        discountsEntity.setCode(code);
        discountsEntity.setValue(value);
    }
}
