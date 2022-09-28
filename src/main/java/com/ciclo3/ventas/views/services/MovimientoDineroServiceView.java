package com.ciclo3.ventas.views.services;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.entities.MovimientoDineroEntity;
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
public class MovimientoDineroServiceView {
    private  String urlBase = "http://localhost:8080/enterprises";
    //private String urlBase = "https://warriorsmisiontic.herokuapp.com/enterprises";
    private List<MovimientoDineroEntity> movimientos;

    private HttpHeaders obtenerHeader(EmpleadoEntity usuario) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", usuario.getToken());
        return headers;
    }

    public void listar(EmpleadoEntity usuario) {
        String url = urlBase + "/" + usuario.getEmpresa().getId() + "/movements";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<MovimientoDineroEntity[]> response;
        response = restTemplate.exchange(url, HttpMethod.GET, request, MovimientoDineroEntity[].class);
        movimientos = Arrays.asList(response.getBody());
    }

    public Page<MovimientoDineroEntity> listarPagina(Pageable pageable, EmpleadoEntity usuario) {
//        if (movimientos == null) {
            listar(usuario);
//        }
        int tamañoPagina = pageable.getPageSize();
        int paginaActual = pageable.getPageNumber();
        int posicionInicial = tamañoPagina * paginaActual;
        List<MovimientoDineroEntity> lista;
        if (movimientos.size() < posicionInicial) {
            lista = Collections.emptyList();
        } else {
            int posicionFinal = Math.min(posicionInicial + tamañoPagina, movimientos.size());
            lista = movimientos.subList(posicionInicial, posicionFinal);
        }

        Page<MovimientoDineroEntity> empleadosPage = new PageImpl<>(lista, PageRequest.of(paginaActual, tamañoPagina),
                movimientos.size());
        return empleadosPage;
    }

    public MovimientoDineroEntity guardar(MovimientoDineroEntity movimiento, EmpleadoEntity usuario) {
        String url = urlBase + "/" + usuario.getEmpresa().getId() + "/movements";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);

        HttpEntity<MovimientoDineroEntity> request = new HttpEntity<MovimientoDineroEntity>(movimiento, headers);

        ResponseEntity<MovimientoDineroEntity> response;
        if (movimiento.getId() == 0) {
            response = restTemplate.exchange(url, HttpMethod.POST, request, MovimientoDineroEntity.class);
        } else {
            url = url + "/" + movimiento.getId();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(600000);
            requestFactory.setReadTimeout(600000);

            restTemplate.setRequestFactory(requestFactory);
            response = restTemplate.exchange(url, HttpMethod.PATCH, request, MovimientoDineroEntity.class);
        }
        return response.getBody();
    }

    public int encontrarPagina(long id, int tamañoPagina) {
        int indice = IntStream.range(0, movimientos.size())
                .filter(i -> movimientos.get(i).getId() == id)
                .findFirst()
                .orElse(-1);

        int pagina = (int) (indice / tamañoPagina) + 1;
        return pagina;
    }

    public MovimientoDineroEntity buscarPorId(long id, EmpleadoEntity usuario) {
        String url = urlBase + "/" + usuario.getEmpresa().getId() + "/movements/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<MovimientoDineroEntity> response = restTemplate.exchange(url, HttpMethod.GET, request, MovimientoDineroEntity.class);
        return response.getBody();
    }

    public boolean borrar(long id, EmpleadoEntity usuario) {
        String url = urlBase + "/" + usuario.getEmpresa().getId() + "/movements/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try{
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Boolean.class);
            return response.getBody();
        }
        catch(Exception ex){
            return false;
        }
    }

    public double totalPorEmpresa(EmpleadoEntity usuario) {
        String url = urlBase + "/" + usuario.getEmpresa().getId() + "/movements/sum";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET, request, Double.class);
        return response.getBody();
    }
}
