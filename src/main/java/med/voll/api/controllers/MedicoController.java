package med.voll.api.controllers;


import jakarta.validation.Valid;
import med.voll.api.medico.CadastroMedico;
import med.voll.api.medico.ListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
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
        return medicoRepository.findAll(pagination).map(ListagemMedico::new);
    }
}
