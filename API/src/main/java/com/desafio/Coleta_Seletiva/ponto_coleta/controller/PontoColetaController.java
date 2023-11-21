package com.desafio.Coleta_Seletiva.ponto_coleta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desafio.Coleta_Seletiva.administradora.services.AdministradoraService;
import com.desafio.Coleta_Seletiva.material.services.MaterialService;
import com.desafio.Coleta_Seletiva.ponto_coleta.dto.PontoColetaCreateDTO;
import com.desafio.Coleta_Seletiva.ponto_coleta.dto.mapper.PontoColetaMapper;
import com.desafio.Coleta_Seletiva.ponto_coleta.model.PontoColeta;
import com.desafio.Coleta_Seletiva.ponto_coleta.services.PontoColetaService;
import com.desafio.Coleta_Seletiva.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pontos de Coleta", description = "Contém todas as operações possíveis de serem realizadas com a entidade Ponto de Coleta.")
@RestController
@RequestMapping("api/pontos")
public class PontoColetaController {
  @Autowired
  private PontoColetaService pontoColetaService;
  @Autowired
  private AdministradoraService administradoraService;
  @Autowired
  private MaterialService materialService;

  public PontoColetaController(PontoColetaService pontoColetaService) {
    this.pontoColetaService = pontoColetaService;
  }

  @Operation(summary = "Criar um novo ponto de coleta", description = "Recurso para criar um novo ponto de coleta", responses = {
      @ApiResponse(responseCode = "200", description = "Ponto de coleta criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PontoColeta.class))),
      @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
  })

  @PostMapping
  public ResponseEntity<PontoColeta> create(@RequestBody PontoColetaCreateDTO dto) {
    PontoColeta created = pontoColetaService
        .create(new PontoColetaMapper(administradoraService, materialService).toPontoColeta(dto));
    return ResponseEntity.ok().body(created);
  }
}
