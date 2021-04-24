import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/paciente">
      <Translate contentKey="global.menu.entities.paciente" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/seccion-a">
      <Translate contentKey="global.menu.entities.seccionA" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/seccion-b">
      <Translate contentKey="global.menu.entities.seccionB" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/seccion-c">
      <Translate contentKey="global.menu.entities.seccionC" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/seccion-d">
      <Translate contentKey="global.menu.entities.seccionD" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vocabulario">
      <Translate contentKey="global.menu.entities.vocabulario" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/uso-lenguaje">
      <Translate contentKey="global.menu.entities.usoLenguaje" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/forma-verbal">
      <Translate contentKey="global.menu.entities.formaVerbal" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/frase-compleja">
      <Translate contentKey="global.menu.entities.fraseCompleja" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cuestionario">
      <Translate contentKey="global.menu.entities.cuestionario" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
