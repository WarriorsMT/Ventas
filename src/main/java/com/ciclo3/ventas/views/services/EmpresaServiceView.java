package com.ciclo3.ventas.views.services;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.entities.EmpresaEntity;
import com.ciclo3.ventas.entities.RespuestaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class EmpresaServiceView {
    //private String urlBase = "http://localhost:8080/enterprises";
    private String urlBase = "https://warriorsmisiontic.herokuapp.com/enterprises";

    private List<EmpresaEntity> empresas;

    public List<EmpresaEntity> getLista() {
        return empresas;
    }

    private HttpHeaders obtenerHeader(EmpleadoEntity usuario) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", usuario.getToken());
        return headers;
    }

    public void listar(EmpleadoEntity usuario) {
        String url = urlBase + "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<EmpresaEntity[]> response = restTemplate.exchange(url, HttpMethod.GET, request, EmpresaEntity[].class);
        empresas = Arrays.asList(response.getBody());
    }

    public Page<EmpresaEntity> listarPagina(Pageable pageable, EmpleadoEntity usuario) {
//        if (empresas == null) {
        listar(usuario);
//        }
        int tamañoPagina = pageable.getPageSize();
        int paginaActual = pageable.getPageNumber();
        int posicionInicial = tamañoPagina * paginaActual;
        List<EmpresaEntity> lista;
        if (empresas.size() < posicionInicial) {
            lista = Collections.emptyList();
        } else {
            int posicionFinal = Math.min(posicionInicial + tamañoPagina, empresas.size());
            lista = empresas.subList(posicionInicial, posicionFinal);
        }

        Page<EmpresaEntity> empresasPage = new PageImpl<>(lista, PageRequest.of(paginaActual, tamañoPagina),
                empresas.size());
        return empresasPage;
    }

    public RespuestaEntity guardar(EmpresaEntity empresa, EmpleadoEntity usuario) {
        String url = urlBase + "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);

        HttpEntity<EmpresaEntity> request = new HttpEntity<EmpresaEntity>(empresa, headers);

        ResponseEntity<RespuestaEntity> response;
        if (empresa.getId() == 0) {
            response = restTemplate.exchange(url, HttpMethod.POST, request, RespuestaEntity.class);
        } else {
            url = url + "/" + empresa.getId();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(600000);
            requestFactory.setReadTimeout(600000);

            restTemplate.setRequestFactory(requestFactory);
            response = restTemplate.exchange(url, HttpMethod.PATCH, request, RespuestaEntity.class);
        }
        return response.getBody();
    }

    public int encontrarPagina(long id, int tamañoPagina) {
        int indice = IntStream.range(0, empresas.size())
                .filter(i -> empresas.get(i).getId() == id)
                .findFirst()
                .orElse(-1);

        int pagina = (int) (indice / tamañoPagina) + 1;
        return pagina;
    }

    public EmpresaEntity buscarPorId(long id, EmpleadoEntity usuario) {
        String url = urlBase + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<EmpresaEntity> response = restTemplate.exchange(url, HttpMethod.GET, request, EmpresaEntity.class);
        return response.getBody();
    }

    public boolean borrar(long id, EmpleadoEntity usuario) {
        String url = urlBase + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Boolean.class);
            return response.getBody();
        } catch (Exception ex) {
            return false;
        }
    }
}
