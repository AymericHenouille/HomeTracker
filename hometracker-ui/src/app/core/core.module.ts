import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ModulesService } from './services/modules.service';
import { RedirectService } from './services/redirect.service';
import { RouterModule } from '@angular/router';
import { FilteredModulesService } from './services/filtered-modules.service';

@NgModule({
  imports: [ HttpClientModule, RouterModule ],
  providers: [ ModulesService, RedirectService, FilteredModulesService ]
})
export class CoreModule { }
