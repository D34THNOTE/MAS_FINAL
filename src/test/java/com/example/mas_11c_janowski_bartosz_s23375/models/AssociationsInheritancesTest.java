package com.example.mas_11c_janowski_bartosz_s23375.models;

import static org.junit.jupiter.api.Assertions.*;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Customer;
import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Employee;
import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Person;
import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.ShippingAddress;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.InstrumentImplementations.Guitar;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.InstrumentImplementations.GuitarType;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.InstrumentStatus;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Materials.*;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StoreAddress;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Purchase;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.PurchaseStatus;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.*;
import com.example.mas_11c_janowski_bartosz_s23375.services.InstrumentService;
import com.example.mas_11c_janowski_bartosz_s23375.services.PersonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class AssociationsInheritancesTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MusicStoreRepository musicStoreRepository;

    @Autowired
    private StorageRoomRepository storageRoomRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PersonService personService;

    @Autowired
    private InstrumentService instrumentService;

    Person person1, person2;

    MusicStore musicStore;

    Employee employee1, employee2;

    Customer customer1, customer2;

    @BeforeEach
    public void initializeData() {
        musicStore = MusicStore.builder()
                .storeName("Test music store")
                .floorsNumber(2)
                .storeAddress(new StoreAddress("ul.Cyfrowa", "68b", "Warsaw", "02-245", "Poland"))
                .build();

        person1 = Person.builder()
                .firstName("sddf")
                .lastName("fef")
                .dateOfBirth(LocalDate.of(1995, 10, 23))
                .phoneNumbers(new HashSet<>(Arrays.asList("674835735", "235964732", "486346948")))
                .build();

        person2 = Person.builder()
                .firstName("sddf")
                .lastName("fef")
                .dateOfBirth(LocalDate.of(1993, 9, 17))
                .phoneNumbers(new HashSet<>(Arrays.asList("457068424", "689567294", "5479674754")))
                .build();

        employee1 = Employee.builder()
                .owner(person1)
                .hireDate(LocalDate.of(2019, 9, 12))
                .hourlyRate(12.0)
                .isManager(true)
                .worksIn(musicStore)
                .build();

        employee2 = Employee.builder()
                .owner(person2)
                .hireDate(LocalDate.of(2020, 9, 12))
                .hourlyRate(13.0)
                .isManager(false)
                .worksIn(musicStore)
                .build();

        customer1 = Customer.builder()
                .owner(person1)
                .loyaltyPoints(0)
                .shippingAddress(new ShippingAddress("ul.Cyfrowa", "68b", "Warsaw", "02-245", "Poland"))
                .build();

        person1.setEmployee(employee1);
        person2.setEmployee(employee2);
        person1.setCustomer(customer1);

        musicStoreRepository.save(musicStore);
    }

    @Test
    public void testPersonSave() {
        personService.savePerson(person1);
        Optional<Person> fetchTest = personRepository.findById(person1.getIdPerson());
        assertTrue(fetchTest.isPresent());
    }

    @Test
    public void testEmployeeSave() {
        personService.savePerson(person1);

        Optional<Person> fetchTestPerson = personRepository.findById(person1.getIdPerson());
        assertTrue(fetchTestPerson.isPresent());

        assertEquals(3, person1.getPhoneNumbers().size());

        Optional<Employee> fetchTestEmployee = employeeRepository.findById(employee1.getIdEmployee());
        assertTrue(fetchTestEmployee.isPresent());

        assertEquals(person1, employee1.getOwner());

        // testing if Person persists if we remove the related Employee
        employeeRepository.delete(employee1);

        fetchTestEmployee = employeeRepository.findById(employee1.getIdEmployee());
        assertTrue(fetchTestEmployee.isEmpty());

        fetchTestPerson = personRepository.findById(person1.getIdPerson());
        assertTrue(fetchTestPerson.isPresent());
    }

    @Test
    public void testMusicStoreAssociations() {
        StorageRoom storageRoom1 = StorageRoom.builder()
                .location("hnusdhnfusdfgf sfkmdjkfsog")
                .owner(musicStore)
                .build();
        musicStore.getStorageRooms().add(storageRoom1);
        musicStoreRepository.save(musicStore);
        storageRoomRepository.save(storageRoom1);

        musicStoreRepository.delete(musicStore);

        Optional<StorageRoom> fetchTestStorage = storageRoomRepository.findById(storageRoom1.getIdStorageRoom());
        assertTrue(fetchTestStorage.isEmpty());
    }

    @Test
    public void testOrderedByPurchase() {
        Guitar guitar1 = Guitar.builder()
                .modelName("Epiphone")
                .price(60.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .numberOfStrings(6)
                .guitarType(GuitarType.ACOUSTIC)
                .heightInMillimeters(1000)
                .widthInMillimeters(60)
                .depthInMillimeters(50)
                .build();

        Guitar guitar2 = Guitar.builder()
                .modelName("Dowina")
                .price(500.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .numberOfStrings(6)
                .guitarType(GuitarType.ACOUSTIC)
                .heightInMillimeters(1000)
                .widthInMillimeters(60)
                .depthInMillimeters(50)
                .build();
        instrumentRepository.save(guitar1);
        instrumentRepository.save(guitar2);

        personService.savePerson(person1);

        Optional<Customer> fetchCustomerTest = customerRepository.findById(customer1.getIdCustomer());
        assertTrue(fetchCustomerTest.isPresent());

        Purchase purchase1 = Purchase.builder()
                .customer(customer1)
                .instrument(guitar1)
                .purchaseStatus(PurchaseStatus.PAID)
                .purchaseDate(LocalDateTime.of(2023, 6, 12, 8, 3, 4))
                .build();
        Purchase purchase2 = Purchase.builder()
                .customer(customer1)
                .instrument(guitar1)
                .purchaseStatus(PurchaseStatus.PAID)
                .purchaseDate(LocalDateTime.of(2023, 6, 12, 9, 7, 4))
                .build();
        Purchase purchase3 = Purchase.builder()
                .customer(customer1)
                .instrument(guitar1)
                .purchaseStatus(PurchaseStatus.PAID)
                .purchaseDate(LocalDateTime.of(2023, 6, 12, 8, 2, 4))
                .build();

        customer1.getPurchases().add(purchase1);
        customer1.getPurchases().add(purchase2);
        customer1.getPurchases().add(purchase3);

        customerRepository.save(customer1);
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);
        purchaseRepository.save(purchase3);


        Optional<Customer> fetchedCustomer = customerRepository.findById(customer1.getIdCustomer());
        assertTrue(fetchedCustomer.isPresent());

        List<Purchase> hopefullyOrderedList = fetchedCustomer.get().getPurchases();
        assertEquals(purchase2, hopefullyOrderedList.get(0));
        assertEquals(purchase1, hopefullyOrderedList.get(1));
        assertEquals(purchase3, hopefullyOrderedList.get(2));
    }

    @Test
    public void testServicesInstrumentSave() {
        Instrument guitar1 = Guitar.builder()
                .modelName("Test")
                .price(60.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .numberOfStrings(6)
                .guitarType(GuitarType.ACOUSTIC)
                .heightInMillimeters(1000)
                .widthInMillimeters(60)
                .depthInMillimeters(50)
                .build();

        Wood wood = Wood.builder()
                .woodType(WoodType.MAPLE)
                .woodFinishType(WoodFinishType.NATURAL)
                .instrument(guitar1)
                .build();

        guitar1.setWood(wood);

        instrumentService.saveInstrument(guitar1);
    }



}
