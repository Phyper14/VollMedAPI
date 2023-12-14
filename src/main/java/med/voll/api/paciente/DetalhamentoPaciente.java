package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DetalhamentoPaciente(Long id, String cpf, String nome, String email, String telefone, Endereco endereco) {
    public DetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getCpf(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());
    }
}
