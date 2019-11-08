package guru.springframework.msscbrewery.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.controller.v2.BeerControllerV2;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerControllerV2.class)
public class BeerControllerTest
{
    @MockBean
    BeerServiceV2 beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDtoV2 validBeer;

    private static final String beerServiceUri = "/api/v2/beer/";

    @Before
    public void setUp()
    {
        validBeer = getValidBeerDto();
    }

    @Test
    public void getBeer() throws Exception
    {
        BDDMockito.given(beerService.getBeerById(ArgumentMatchers.any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(MockMvcRequestBuilders.get(beerServiceUri + validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(validBeer.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", Is.is("Beer1")));

    }

    @Test
    public void handlePost() throws Exception
    {
        BeerDtoV2 beerDto = getValidBeerDto();
        beerDto.setId(null);
        BeerDtoV2 savedDto = BeerDtoV2.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        String beeerDtoJson = objectMapper.writeValueAsString(beerDto);

        BDDMockito.given(beerService.saveNewBeer(ArgumentMatchers.any())).willReturn(savedDto);

        mockMvc.perform(MockMvcRequestBuilders.post(beerServiceUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beeerDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void handleUpdate() throws Exception
    {
        BeerDtoV2 beerDto = validBeer;
        beerDto.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(MockMvcRequestBuilders.put(beerServiceUri + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        BDDMockito.then(beerService).should().updateBeer(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    BeerDtoV2 getValidBeerDto()
    {
        return BeerDtoV2.builder()
                .id(UUID.randomUUID())
                .beerName("Beer1")
                // .beerStyle(BeerStyleEnum.ALE.toString())
                .upc(123456789012L)
                .build();
    }
}
