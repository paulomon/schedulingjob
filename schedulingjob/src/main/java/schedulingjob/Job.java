package schedulingjob;

import java.time.LocalDateTime;

public class Job {

	private long id;
	private String descricao;
	private LocalDateTime dataMaximaConclusao;
	private long tempoEstimado;
	
	public boolean tempoEstimadoDentroDoLimite(long limite) {
		return tempoEstimado <= limite;
	}
	
	private Job(long id, String descricao, LocalDateTime dataMaximaConclusao, long tempoEstimado) {
		this.id = id;
		this.descricao = descricao;
		this.dataMaximaConclusao = dataMaximaConclusao;
		this.tempoEstimado = tempoEstimado;
	}
	
	public static class JobBuilder {
		private long id;
		private String descricao;
		private LocalDateTime dataMaximaConclusao;
		private long tempoEstimado;
		
		public JobBuilder id(long id) {
			this.id = id;
			return this;
		}
		public JobBuilder descricao(String descricao) {
			this.descricao = descricao;
			return this;
		}
		public JobBuilder dataMaximaConclusao(LocalDateTime dataMaximaConclusao) {
			this.dataMaximaConclusao = dataMaximaConclusao;
			return this;
		}
		public JobBuilder tempoEstimado(long tempoEstimado) {
			this.tempoEstimado = tempoEstimado;
			return this;
		}

		public Job build() {
			return new Job(id, descricao, dataMaximaConclusao, tempoEstimado);
		}
	}
}
