package med.voll.api.controllers;


import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    @Transactional
    public void CadastrarMedico(@RequestBody @Valid CadastroMedico cadastroMedico) {
        medicoRepository.save(new Medico(cadastroMedico));
    }

    @GetMapping
    public Page<ListagemMedico> BuscarTodosOsMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        return medicoRepository.findAllByAtivoTrue(pagination).map(ListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void AlterarMedicoPorId(@RequestBody @Valid AlteracaoMedico alteracaoMedico) {
        var medico = medicoRepository.getReferenceById(alteracaoMedico.id());
        medico.alterarMedico(alteracaoMedico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void DeletarMedicoPorId(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById((id));
        medico.deletarMedico();
    }
}
