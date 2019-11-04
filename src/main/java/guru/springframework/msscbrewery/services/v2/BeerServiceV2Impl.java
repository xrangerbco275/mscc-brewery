package guru.springframework.msscbrewery.services.v2;

import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import guru.springframework.msscbrewery.web.model.v2.BeerStyleEnum;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@Service
public class BeerServiceV2Impl implements BeerServiceV2
{

    public BeerServiceV2Impl()
    {
    }


    @Override
    public BeerDtoV2 getBeerById(UUID beerId)
    {
        return BeerDtoV2.builder().id(UUID.randomUUID())
                .beerName("Crazy Cat")
                .beerStyle(BeerStyleEnum.LAGER)
                .build();
    }

    @Override
    public BeerDtoV2 saveNewBeer(BeerDtoV2 beerDto)
    {
        return BeerDtoV2.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDtoV2 beerDto)
    {

    }

    @Override
    public void deleteBeer(UUID beerId)
    {

    }
}
