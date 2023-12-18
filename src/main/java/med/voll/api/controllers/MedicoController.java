package med.voll.api.controllers;


import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoMedico> CadastrarMedico(@RequestBody @Valid CadastroMedico cadastroMedico, UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(cadastroMedico);

        medicoRepository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemMedico>> BuscarTodosOsMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        var page = medicoRepository.findAllByAtivoTrue(pagination).map(ListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoMedico> BuscarMedicoPorId(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById((id));
        return ResponseEntity.ok(new DetalhamentoMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalhamentoMedico> AlterarMedicoPorId(@RequestBody @Valid AlteracaoMedico alteracaoMedico) {
        var medico = medicoRepository.getReferenceById(alteracaoMedico.id());
        medico.alterarMedico(alteracaoMedico);

        return ResponseEntity.ok(new DetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> DeletarMedicoPorId(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById((id));
        medico.deletarMedico();

        return ResponseEntity.noContent().build();
    }
}
