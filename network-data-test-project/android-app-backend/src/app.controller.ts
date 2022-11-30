import { Controller, Get } from '@nestjs/common';
import { AppService } from './app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): object {
    
    return {
      "name" : `${this.appService.getHello()} ${Math.random()}`   
    };
  }
}
