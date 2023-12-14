package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoPaciente> CadastrarPaciente(@RequestBody @Valid CadastroPaciente cadastroPaciente, UriComponentsBuilder uriComponentsBuilder) {
        var paciente = new Paciente(cadastroPaciente);
        pacienteRepository.save(paciente);

        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemPaciente>> BuscarTodosOsPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        var page = pacienteRepository.findAllByAtivoTrue(pagination).map(ListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhamentoPaciente> BuscarPacientePorId(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoPaciente(paciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalhamentoPaciente> AlterarPacientePorId(@RequestBody @Valid AlteracaoPaciente alteracaoPaciente) {
         Paciente paciente = pacienteRepository.getReferenceById(alteracaoPaciente.id());
         paciente.alterarPaciente(alteracaoPaciente);

         return ResponseEntity.ok(new DetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> DeletarPacientePorId(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById((id));
        paciente.deletarPaciente();

        return ResponseEntity.noContent().build();
    }
}
