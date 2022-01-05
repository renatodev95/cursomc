package com.renatodev.cursomc.controller;

import com.renatodev.cursomc.ApplicationConfigTest;
import com.renatodev.cursomc.domain.Cliente;
import com.renatodev.cursomc.domain.enums.TipoCliente;
import com.renatodev.cursomc.services.ClienteService;
import com.renatodev.cursomc.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("ClienteControllerTest")
class ClienteControllerTest extends ApplicationConfigTest {

    @MockBean
    private ClienteService service;

    @Autowired
    private MockMvc mockMvc;

    private static final String PATH = "/clientes/1";

    @Test
    @DisplayName("Deve retornar uma venda no GET")
    void deveRetornarUmClienteNoGET() throws Exception {
        Cliente cliente = new Cliente(1L, null, null, null, TipoCliente.PESSOAFISICA);
        when(service.buscar(anyLong())).thenReturn(cliente);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        String jsonEsperado = "{\"id\":1,\"nome\":null,\"email\":null,\"cpfOuCnpj\":null,\"tipo\":\"PESSOAFISICA\",\"enderecos\":[],\"telefones\":[]}";
        Assertions.assertEquals(jsonEsperado, response.getContentAsString());
    }

    @Test
    @DisplayName("Deve retornar uma venda no GET")
    void deveRetornarUmErroNo() throws ObjectNotFoundException {
        Cliente cliente = new Cliente(2L, null, null, null, TipoCliente.PESSOAFISICA);
        when(service.buscar(anyLong())).thenThrow(ObjectNotFoundException.class);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.buscar(cliente.getId()));
    }
}
