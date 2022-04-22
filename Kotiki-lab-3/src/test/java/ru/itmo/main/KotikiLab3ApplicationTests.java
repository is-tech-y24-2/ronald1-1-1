package ru.itmo.main;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.itmo.main.dao.model.CatsEntity;
import ru.itmo.main.dao.model.Color;
import ru.itmo.main.dao.model.OwnersEntity;
import ru.itmo.main.dao.repository.CatRepository;
import ru.itmo.main.dao.repository.OwnerRepository;
import ru.itmo.main.service.CatService;
import ru.itmo.main.service.OwnerService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class KotikiLab3ApplicationTests {

    @Autowired
    private CatService catService;

    @Autowired
    private OwnerService ownerService;

    @MockBean
    private CatRepository catRepository;

    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void testCatService(){
        CatsEntity catsEntity = catService.saveCat("pol", "lop", Color.BLACK, new OwnersEntity(), new Date(12423423));
        Assert.assertEquals("pol", catsEntity.getName());
        Assert.assertEquals("lop", catsEntity.getBreed());
        Assert.assertEquals(Color.BLACK, catsEntity.getColor());
        Assert.assertEquals(new Date(12423423), catsEntity.getBirthday());
        Mockito.verify(catRepository, Mockito.times(1)).save(catsEntity);
        Mockito.doReturn(Arrays.asList(catsEntity)).when(catRepository).findAllByColor(Color.BLACK);
        Assert.assertEquals(catsEntity, catService.getAllCatsByColor(Color.BLACK).get(0));
    }

    @Test
    void testOwnerService(){
        OwnersEntity ownersEntity = ownerService.saveOwner("popo",new Date(1231243));
        Assert.assertEquals("popo", ownersEntity.getName());
        Assert.assertEquals(new Date(1231243), ownersEntity.getBirthday());
        Mockito.verify(ownerRepository, Mockito.times(1)).save(ownersEntity);
        Mockito.doReturn(Optional.ofNullable(ownersEntity)).when(ownerRepository).findById(0);
        Assert.assertEquals(ownersEntity, ownerService.getOwnerById(0));

    }
}
