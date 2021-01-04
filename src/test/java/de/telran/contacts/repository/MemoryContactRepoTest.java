package de.telran.contacts.repository;

import de.telran.contacts.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryContactRepoTest {

    MemoryContactRepo contactRepo;

    @BeforeEach
    public void init() {
        contactRepo = new MemoryContactRepo();
    }

    @Test
    public void testGetAll_noElements_emptyList() {
        assertEquals(0, contactRepo.getAll().size());
    }

    @Test
    public void testAddAndGet_oneContact_listWithOneContact() {
        Contact vasya = new Contact("vasya", "123");
        contactRepo.add(vasya);

        List<Contact> contacts = contactRepo.getAll();
        assertEquals(1, contacts.size());

        Contact fromRepo = contacts.get(0);
        assertEquals(vasya.name, fromRepo.name);
        assertEquals(vasya.number, fromRepo.number);
    }

    @Test
    public void testAddAndGet_threeContacts_threeContacts() {
        Contact vasya = new Contact("vasya", "123");
        Contact petya = new Contact("petya", "456");
        Contact masha = new Contact("masha", "789");
        contactRepo.add(vasya);
        contactRepo.add(masha);
        contactRepo.add(petya);

        List<Contact> contacts = contactRepo.getAll();
        assertEquals(3, contacts.size());

        Contact vasyaFromRepo = contacts.stream().filter(contact -> contact.id == vasya.id).findFirst().get();
        Contact petyaFromRepo = contacts.stream().filter(contact -> contact.id == petya.id).findFirst().get();
        Contact mashaFromRepo = contacts.stream().filter(contact -> contact.id == masha.id).findFirst().get();

        assertEquals(vasya.name, vasyaFromRepo.name);
        assertEquals(vasya.number, vasyaFromRepo.number);
        assertEquals(masha.name, mashaFromRepo.name);
        assertEquals(masha.number, mashaFromRepo.number);
        assertEquals(petya.name, petyaFromRepo.name);
        assertEquals(petya.number, petyaFromRepo.number);
    }

    @Test
    public void testGet_threeElements_exists() {
        Contact vasya = new Contact("vasya", "123");
        Contact petya = new Contact("petya", "456");
        Contact masha = new Contact("masha", "789");
        contactRepo.add(vasya);
        contactRepo.add(masha);
        contactRepo.add(petya);

        Contact mashaFromRepo = contactRepo.getId(masha.id);
        assertEquals(masha.name, mashaFromRepo.name);
        assertEquals(masha.number, mashaFromRepo.number);
    }

    @Test
    public void testGet_threeElements_notExist() {
        Contact vasya = new Contact("vasya", "123");
        Contact petya = new Contact("petya", "456");
        Contact masha = new Contact("masha", "789");
        contactRepo.add(vasya);
        contactRepo.add(masha);
        contactRepo.add(petya);

        Contact fromRepo = contactRepo.getId(masha.id);
        assertNull(fromRepo);
    }

    @Test
    public void testRemove_threeElements_exists() {
        Contact vasya = new Contact("vasya", "123");
        Contact petya = new Contact("petya", "456");
        Contact masha = new Contact("masha", "789");
        contactRepo.add(vasya);
        contactRepo.add(masha);
        contactRepo.add(petya);

        contactRepo.remove(vasya.id);
        assertEquals(2, contactRepo.getAll().size());

        Contact petyaFromRepo = contactRepo.getId(petya.id);
        Contact mashaFromRepo = contactRepo.getId(masha.id);
        Contact vasyaFromRepo = contactRepo.getId(vasya.id);

        assertNull(vasyaFromRepo);

        assertEquals(masha.name, mashaFromRepo.name);
        assertEquals(masha.number, mashaFromRepo.number);
        assertEquals(petya.name, petyaFromRepo.name);
        assertEquals(petya.number, petyaFromRepo.number);
    }

    @Test
    public void testEdit_threeElements_exists() {
        Contact vasya = new Contact("vasya", "123");
        Contact petya = new Contact("petya", "456");
        Contact masha = new Contact("masha", "789");
        contactRepo.add(vasya);
        contactRepo.add(masha);
        contactRepo.add(petya);

        Contact petyaToChange = new Contact("pet", "2121212");
        petyaToChange.id = petya.id;

        contactRepo.edit(petyaToChange);

        Contact petyaFromRepo = contactRepo.getId(petya.id);
        assertEquals(petyaToChange.name, petyaFromRepo.name);
        assertEquals(petyaToChange.number, petyaFromRepo.number);
    }
}