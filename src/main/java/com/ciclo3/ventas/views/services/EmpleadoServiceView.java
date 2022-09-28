package com.ciclo3.ventas.views.services;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.entities.RespuestaEntity;
import com.ciclo3.ventas.util.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class EmpleadoServiceView {
    private String urlBase = "http://localhost:8080/users";

    private List<EmpleadoEntity> empleados;

    public EmpleadoEntity login(String correo, String clave) {
        String url = urlBase + "/login?usuario=" + correo + "&clave=" + clave;
        RestTemplate restTemplate = new RestTemplate();

        EmpleadoEntity usuario = restTemplate.getForObject(url, EmpleadoEntity.class);
        return usuario;
    }

    public List<EmpleadoEntity> getLista(){
        return empleados;
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

        ResponseEntity<EmpleadoEntity[]> response;
        if(usuario.getRol().toString().equals("SUPERADMINISTRADOR")){
            response = restTemplate.exchange(url, HttpMethod.GET, request, EmpleadoEntity[].class);
        }else{
            url = urlBase + "/enterprise/" + usuario.getEmpresa().getId();
            response = restTemplate.exchange(url, HttpMethod.GET, request, EmpleadoEntity[].class);
        }
        empleados = Arrays.asList(response.getBody());
    }

    public Page<EmpleadoEntity> listarPagina(Pageable pageable, EmpleadoEntity usuario) {
//        if (empleados == null) {
            listar(usuario);
//        }
        int tamañoPagina = pageable.getPageSize();
        int paginaActual = pageable.getPageNumber();
        int posicionInicial = tamañoPagina * paginaActual;
        List<EmpleadoEntity> lista;
        if (empleados.size() < posicionInicial) {
            lista = Collections.emptyList();
        } else {
            int posicionFinal = Math.min(posicionInicial + tamañoPagina, empleados.size());
            lista = empleados.subList(posicionInicial, posicionFinal);
        }

        Page<EmpleadoEntity> empleadosPage = new PageImpl<>(lista, PageRequest.of(paginaActual, tamañoPagina),
                empleados.size());
        return empleadosPage;
    }

    public RespuestaEntity guardar(EmpleadoEntity empleado, EmpleadoEntity usuario) {
        String url = urlBase + "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);

        HttpEntity<EmpleadoEntity> request = new HttpEntity<EmpleadoEntity>(empleado, headers);

        ResponseEntity<RespuestaEntity> response;
        if (empleado.getId() == 0) {
            response = restTemplate.exchange(url, HttpMethod.POST, request, RespuestaEntity.class);
        } else {
            url = url + "/" + empleado.getId();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(600000);
            requestFactory.setReadTimeout(600000);

            restTemplate.setRequestFactory(requestFactory);
            response = restTemplate.exchange(url, HttpMethod.PATCH, request, RespuestaEntity.class);
        }
        return response.getBody();
    }

    public int encontrarPagina(long id, int tamañoPagina) {
        int indice = IntStream.range(0, empleados.size())
                .filter(i -> empleados.get(i).getId() == id)
                .findFirst()
                .orElse(-1);

        int pagina = (int) (indice / tamañoPagina) + 1;
        return pagina;
    }

    public EmpleadoEntity buscarPorId(long id, EmpleadoEntity usuario) {
        String url = urlBase + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = obtenerHeader(usuario);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<EmpleadoEntity> response = restTemplate.exchange(url, HttpMethod.GET, request, EmpleadoEntity.class);
        return response.getBody();
    }

    public List<String> listarRoles() {
        List<String> roles = new ArrayList<>();
        for (Rol r : Rol.values()){
            roles.add(r.toString());
        }
        return roles;
    }

    public boolean borrar(long id, EmpleadoEntity usuario) {
        String url = urlBase + "/" + id;
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
}
