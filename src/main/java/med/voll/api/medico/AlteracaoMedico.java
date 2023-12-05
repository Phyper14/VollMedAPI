package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.CadastroEndereco;

public record AlteracaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        CadastroEndereco endereco
) {}
