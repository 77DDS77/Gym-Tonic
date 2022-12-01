export class Exercise {
  id!:number;
  name:string;
  type:string;
  muscle:string;
  equipment:string;
  difficulty:string;
  instruction:string;

  constructor(name:string, type:string, muscle:string, equipment:string, difficulty:string, instruction:string){
    this.name = name;
    this.type = type;
    this.muscle = muscle;
    this.equipment = equipment;
    this.difficulty = difficulty;
    this.instruction = instruction;
  }
}
