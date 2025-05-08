export class LocalTime {
    horas!: number;
    minutos!: number;
    segundos!: number;

    constructor(timeString: string){
        const [hours, minutes, seconds] = timeString.split(':').map(Number);
        this.horas = hours;
        this.minutos = minutes;
        this.segundos = seconds || 0;
    }

    toDate(): Date {
        const date = new Date();
        date.setHours(this.horas, this.minutos, this.segundos, 0);
        return date;
    }

    toString(): string {
        return `${this.horas.toString().padStart(2, '0')}:${this.minutos.toString().padStart(2, '0')}:${this.segundos.toString().padStart(2, '0')}`;
    }
}
