package med.voll.api.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.Medico;

@Table(name = "pacientes")
@Entity(name = "paciente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Paciente {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private boolean ativo;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;

    public Paciente (CadastroPaciente cadastroPaciente) {
        this.ativo = true;
        this.nome = cadastroPaciente.nome();
        this.email = cadastroPaciente.email();
        this.telefone = cadastroPaciente.telefone();
        this.cpf = cadastroPaciente.cpf();
        this.endereco = new Endereco(cadastroPaciente.endereco());
    }

    public void deletarPaciente() {this.ativo = false;}

    public void alterarPaciente(AlteracaoPaciente alteracaoPaciente) {
        if(alteracaoPaciente.nome() != null) {
            this.nome = alteracaoPaciente.nome();
        }
        if(alteracaoPaciente.telefone() != null) {
            this.telefone = alteracaoPaciente.telefone();
        }
        if(alteracaoPaciente.endereco() != null) {
            this.endereco.alterarEndereco(alteracaoPaciente.endereco());
        }
    }
}
