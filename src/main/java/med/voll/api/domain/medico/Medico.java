package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private boolean ativo;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;


    public Medico(CadastroMedico cadastroMedico) {
        this.ativo = true;
        this.nome = cadastroMedico.nome();
        this.email = cadastroMedico.email();
        this.telefone = cadastroMedico.telefone();
        this.crm = cadastroMedico.crm();
        this.especialidade = cadastroMedico.especialidade();
        this.endereco = new Endereco(cadastroMedico.endereco());
    }

    public void alterarMedico(AlteracaoMedico alteracaoMedico) {
        if(alteracaoMedico.nome() != null) {
            this.nome = alteracaoMedico.nome();
        }
        if(alteracaoMedico.telefone() != null) {
            this.telefone = alteracaoMedico.telefone();
        }
        if(alteracaoMedico.endereco() != null) {
            this.endereco.alterarEndereco(alteracaoMedico.endereco());
        }
    }

    public void deletarMedico() {
        this.ativo = false;
    }
}
