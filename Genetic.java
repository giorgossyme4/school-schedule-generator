import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic
{
	private ArrayList<Schedule> population;
	private ArrayList<Integer> fitnessBounds;
	private ArrayList<Teacher> teachers = MainApp.Teachers;
	private ArrayList<Class> classes = MainApp.allClasses;
	
	public Genetic()
	{
		this.population = null;
		this.fitnessBounds = null;
	}

	public Schedule geneticAlgorithm(int populationSize, double mutationProbability, int minimumFitness, int maximumSteps)
	{
		initializePopulation(populationSize);
		Random r = new Random();
		for(int step = 0; step < maximumSteps; step++)
		{
			System.out.println("Step: "+ step +"...");
			ArrayList<Schedule> newPopulation = new ArrayList<Schedule>();
			for(int i = 0; i < populationSize; i++)
			{
				int xIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
				Schedule x = this.population.get(xIndex);
				int yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
				while(yIndex == xIndex)
				{
					yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
				}
				Schedule y = this.population.get(yIndex);
				Schedule child = this.reproduce(x, y);
				if(r.nextDouble() < mutationProbability)
				{
					child.mutate();
				}

				newPopulation.add(child);
			}
			this.population = new ArrayList<Schedule>(newPopulation);

			sortPopulation();
			
			if(this.population.get(0).getFitness() == 0)
			{
                System.out.println("Finished after " + step + " steps.");
				return this.population.get(0);
			}
			this.updateFitnessBounds();
		}

        System.out.println("Finished after " + maximumSteps + " steps.");
		return this.population.get(0);
	}

	public void initializePopulation(int populationSize)
	{
		this.population = new ArrayList<Schedule>();
		for(int i = 0; i < populationSize; i++)
		{
			this.population.add(new Schedule());
		}
		this.updateFitnessBounds();
	}

	public void updateFitnessBounds()
	{
		sortPopulation();
		this.fitnessBounds = new ArrayList<Integer>();
		for (int i = 0; i < this.population.size(); i++)
		{
			int size = this.population.get(this.population.size() - 1).getFitness() - this.population.get(i).getFitness();
			if (size == 0) {
				size = 1;
			}

			for(int j = 0; j < size; j++)
			{
				fitnessBounds.add(i);
			}
		}
	}

	public void sortPopulation() {
		int n = this.population.size();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (this.population.get(i).getFitness() > this.population.get(j).getFitness()) {
					Schedule temp = this.population.get(i);  
					this.population.set(i, this.population.get(j));   
					this.population.set(j, temp);
				}
			}
		}
	}

	public Schedule reproduce(Schedule x, Schedule y)
	{
		Random r = new Random();
		
		int intersectionPoint = r.nextInt(classes.size() - 1) + 1;
		
		Class[][] childGenes = new Class[(x.getGenes()).length][(x.getGenes()[0]).length];


		for (int i = 0; i < x.getGenes().length; i++) {
			for (int j = 0; j < x.getGenes()[0].length; j++) {
				for (int m = 0; m < intersectionPoint; m++) {
					if ((x.getGenes()[i][j] == classes.get(m)) && (childGenes[i][j] == null)) {
						childGenes[i][j] = x.getGenes()[i][j];
						break;
					}
				}
			}
		}

		for (int i = 0; i < y.getGenes().length; i++) {
			for (int j = 0; j < y.getGenes()[0].length; j++) {
				for (int m = intersectionPoint; m < classes.size(); m++) {
					if ((y.getGenes()[i][j] == classes.get(m)) && (childGenes[i][j] == null)) {
						childGenes[i][j] = y.getGenes()[i][j];
						break;
					} else if ((y.getGenes()[i][j] == classes.get(m)) && (childGenes[i][j] != null)) {

						ArrayList<Integer> indexes = new ArrayList<Integer>();
						for (int k = 0; k < x.getGenes()[0].length; k++) {
							indexes.add(k);
						}

						int index = r.nextInt(indexes.size());
						int column = indexes.get(index);
						indexes.remove(index);
						int row = i;

						while (childGenes[row][column] != null) {
							if (indexes.size() == 0) {
								int ix = r.nextInt(y.getGenes()[i][j].getTeachers().size());
								Teacher new_t= y.getGenes()[i][j].getTeachers().get(ix);
								row = new_t.findIndex(teachers);
								
								for (int k = 0; k < x.getGenes()[0].length; k++) {
									indexes.add(k);
								}
								
							}
							index = r.nextInt(indexes.size());
							column = indexes.get(index);
							indexes.remove(index);
						}
						childGenes[i][column] = y.getGenes()[i][j];
					}
				}
			}
		}
		
		Schedule s = new Schedule(childGenes);
		return s;
	}
}
