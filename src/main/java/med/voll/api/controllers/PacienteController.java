package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void CadastrarPaciente(@RequestBody @Valid CadastroPaciente cadastroPaciente) {
        pacienteRepository.save(new Paciente(cadastroPaciente));
    }

    @GetMapping
    public Page<ListagemPaciente> BuscarTodosOsPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        return pacienteRepository.findAllByAtivoTrue(pagination).map(ListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void AlterarPacientePorId(@RequestBody @Valid AlteracaoPaciente alteracaoPaciente) {
         Paciente paciente = pacienteRepository.getReferenceById(alteracaoPaciente.id());
         paciente.alterarPaciente(alteracaoPaciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void DeletarPacientePorId(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById((id));
        paciente.deletarPaciente();
    }
}
